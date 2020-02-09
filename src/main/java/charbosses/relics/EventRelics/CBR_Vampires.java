package charbosses.relics.EventRelics;

import basemod.abstracts.CustomRelic;
import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.cards.colorless.EnBite;
import charbosses.relics.AbstractCharbossRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Bite;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.Anchor;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import evilWithin.EvilWithinMod;
import sneckomod.SneckoMod;
import theHexaghost.util.TextureLoader;

import java.util.ArrayList;


public class CBR_Vampires extends AbstractCharbossRelic
{
    public static String ID = EvilWithinMod.makeID("Vampires");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = AbstractRelic.LandingSound.MAGICAL;


    public CBR_Vampires() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/vampires.png")));

    }


    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list) {
        ArrayList<AbstractBossCard> cardsToRemove = new ArrayList<>();
        for (AbstractBossCard c : list){
            if (c.hasTag(AbstractBossCard.CardTags.STARTER_STRIKE)){
                AbstractBossDeckArchetype.logger.info("Vampires event removed 1 " + c.name + ".");
                cardsToRemove.add(c);
            }
        }
        if (cardsToRemove.size() > 0){
            for (AbstractBossCard c : cardsToRemove){
                list.remove(c);
            }
        }
        for (int i = 0; i < 5; i++) {

            AbstractCharBoss.boss.masterDeck.group.add(new EnBite());
            AbstractBossDeckArchetype.logger.info("Vampires event added 1 Bite.");
        }

    }

    
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    
    @Override
    public void atBattleStart() {
        this.addToBot(new IncreaseMaxHpAction(AbstractCharBoss.boss, -0.3F,false));
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_Vampires();
    }
}
