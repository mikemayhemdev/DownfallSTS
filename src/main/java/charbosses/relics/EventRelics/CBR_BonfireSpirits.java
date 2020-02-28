package charbosses.relics.EventRelics;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.bosses.AbstractCharBoss;
import charbosses.cards.AbstractBossCard;
import charbosses.relics.AbstractCharbossRelic;
import charbosses.relics.CBR_SpiritPoop;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import evilWithin.EvilWithinMod;

import java.util.ArrayList;
import java.util.Collections;


public class CBR_BonfireSpirits extends AbstractCharbossRelic {
    public static String ID = EvilWithinMod.makeID("BonfireSpirits");
    private static RelicTier tier = RelicTier.SPECIAL;
    private static LandingSound sound = LandingSound.MAGICAL;
    public String cardName = "";
    private int descInt = 0;

    public CBR_BonfireSpirits() {
        super(ID, tier, sound, new Texture(EvilWithinMod.assetPath("images/relics/bonfirespirits.png")));
    }


    @Override
    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> list, int actIndex) {
        ArrayList<AbstractBossCard> cardsToRemove = new ArrayList<>();
        Collections.shuffle(list);

        //Prioritize removing a Curse first
        for (AbstractBossCard c : list) {
            if (c.type == AbstractCard.CardType.CURSE) {
                AbstractBossDeckArchetype.logger.info("Bonfire Spirits event removed 1 " + c.name + ".");
                cardsToRemove.add(c);
                cardName = c.name;
                AbstractCharBoss.boss.chosenArchetype.addSpecificRelic(new CBR_SpiritPoop(), AbstractCharBoss.boss, "Bonfire Spirits", list);
                this.descInt = 0;
                this.updateDescription(AbstractCharBoss.boss.chosenClass);
                break;
            }
        }

        //If no Curse was found, prioritize a Starter card next
        if (cardsToRemove.size() == 0) {
            for (AbstractBossCard c : list) {
                if (c.rarity == AbstractCard.CardRarity.BASIC) {
                    AbstractBossDeckArchetype.logger.info("Bonfire Spirits event removed 1 " + c.name + ".");
                    cardsToRemove.add(c);
                    cardName = c.name;
                    this.descInt = 2;
                    this.updateDescription(AbstractCharBoss.boss.chosenClass);
                    break;
                }
            }
        }

        //If no Starters were found, choose a Rare instead.
        if (cardsToRemove.size() == 0) {
            for (AbstractBossCard c : list) {
                if (c.rarity == AbstractCard.CardRarity.RARE) {
                    AbstractBossDeckArchetype.logger.info("Bonfire Spirits event removed 1 " + c.name + ".");
                    cardsToRemove.add(c);
                    cardName = c.name;
                    AbstractCharBoss.boss.increaseMaxHp(10, false);
                    this.descInt = 1;
                    this.updateDescription(AbstractCharBoss.boss.chosenClass);
                    break;
                }
            }
        }

        if (cardsToRemove.size() > 0) {
            for (AbstractBossCard c : cardsToRemove) {
                list.remove(c);
            }
        }
    }

    @Override
    public void updateDescription(final AbstractPlayer.PlayerClass c) {
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }

    @Override
    public String getUpdatedDescription() {
        switch (descInt) {
            case 0:
                return this.cardName + this.DESCRIPTIONS[0];
            case 1:
                return this.cardName + this.DESCRIPTIONS[1];
            case 2:
                return this.cardName + this.DESCRIPTIONS[2];
            default:
                return this.cardName + this.DESCRIPTIONS[0];
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CBR_BonfireSpirits();
    }
}
