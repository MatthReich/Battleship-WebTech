<template>
  <div class="about text-center">
    <br />
    <h1>Battleship</h1>
    <hr />
    <br />
    <battleshipMainGif />
    <br />
    <hr />
    <br />
    <div class="about_howTo">
      <h2>How To Play</h2>
      <br />
      <p>
        The game is played on four grids, two for each player. The grids are
        typically square – usually 10×10 – and the individual squares in the
        grid are identified by letter and number. On one grid the player
        arranges ships and records the shots by the opponent. On the other grid
        the player records their own shots. Before play begins, each player
        secretly arranges their ships on their primary grid. Each ship occupies
        a number of consecutive squares on the grid, arranged either
        horizontally or vertically. The number of squares for each ship is
        determined by the type of the ship. The ships cannot overlap (i.e., only
        one ship can occupy any given square in the grid). The types and numbers
        of ships allowed are the same for each player.
      </p>
      <h3>Gamestates:</h3>
      <br />
      <p>
        <strong>Playersetting:</strong>
        <br />
        Just hit the start button on the <a href="/#/">HOME</a> Page and write
        your Name. Then wait for player 2
      </p>
      <p>
        <strong>Shipsetting:</strong>
        <br />
        the ships has to set the start and the end of the ship
      </p>
      <p>
        <strong>Idle:</strong>
        <br />
        Just press the field you think the opponent has placed a ship
      </p>
      <p><strong>Solved</strong><br />it's a secret ;)</p>
      <p>
        <strong>Saved</strong><br />press SAVE in the navbar
      </p>
      <p>
        <strong>Loaded</strong><br />press LAOD in the navbar
      </p>
    </div>
    <hr />
    <br />
    <div>
      <captionabout />
    </div>
    <br />
    <hr />
    <br />
    <h2>Developer</h2>
    <div class="developer_aboutpage">
      <br />
      <p>Marcel Gaiser</p>
      <p>Matthias Reichenbach</p>
    </div>
    <br />
    <br />
  </div>
</template>

<script>
import battleshipMainGif from "@/components/media/battleshipmaingif";
import captionabout from "@/components/media/captionabout";
export default {
  components: { battleshipMainGif, captionabout }
};
</script>
