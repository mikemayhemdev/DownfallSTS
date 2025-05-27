package slimebound.slimes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class SlimeHelper {
    public static AbstractSlime Pike;
    public static AbstractSlime Cid;

    public static void AtBattleStart() {
        Pike = new Pike();
        Cid = new Cid();
    }

    public static void Update() {
        Pike.update();
        Cid.update();
    }

    public static void Render(SpriteBatch sb) {
        Pike.render(sb);
        Cid.render(sb);
    }

    public static void AtEndOfTurn() {
        Pike.onEndOfTurn();
        Cid.onEndOfTurn();
    }

    public static int GetPikeEnergy(){
        return Pike.energy;
    }

    public static int GetCidEnergy(){
        return Cid.energy;
    }

    public static boolean DoesPikeHaveEnchantment(AbstractSlime.SlimeEnchantmentType enchantToCheck){
        return Pike.isEnchanted(enchantToCheck);
    }

    public static boolean DoesCidHaveEnchantment(AbstractSlime.SlimeEnchantmentType enchantToCheck){
        return Cid.isEnchanted(enchantToCheck);
    }

    public static boolean InCombat() {
        return CardCrawlGame.isInARun() && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }

    //TODO - build in such a way that we might remove a slime. Could be a fun event.
    // unsure if we want to do this with all the cards built out, that'd break literally everything
}

