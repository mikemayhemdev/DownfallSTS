package awakenedOne.relics;

import awakenedOne.AwakenedOneMod;
import awakenedOne.cards.tokens.spells.AbstractSpellCard;
import awakenedOne.util.TexLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;
import static awakenedOne.util.Wiz.getEnemies;

public class EyeOfTheOccult extends CustomRelic {

    //Eye of the Occult

    public static final String ID = AwakenedOneMod.makeID("EyeOfTheOccult");
    private static final Texture IMG = TexLoader.getTexture(makeRelicPath("EyeOfTheOccult.png")); //TODO: Images
    private static final Texture OUTLINE = TexLoader.getTexture(makeRelicOutlinePath("EyeOfTheOccult.png"));

    //Yeah... you need to go look at the Spell cards for this one.
    public EyeOfTheOccult() {
        super(ID, IMG, OUTLINE, RelicTier.SPECIAL, LandingSound.MAGICAL);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if ((card instanceof AbstractSpellCard && card.target == AbstractCard.CardTarget.ALL_ENEMY)) {
            //only flash if relevant
            if (getEnemies().size() > 1) {
                flash();
            }
            if ((card.hasTag(SPELLCARD) && card.target != AbstractCard.CardTarget.SELF)) {
                //only flash if relevant
                if (getEnemies().size() > 1) {
                    flash();
                    if ((card.target != AbstractCard.CardTarget.ENEMY && card.target != AbstractCard.CardTarget.SELF_AND_ENEMY) || card.hasTag(NO_DUPLICATION))
                        return;
                    for (int i = (AbstractDungeon.getCurrRoom()).monsters.monsters.size() - 1; i >= 0; i--) {
                        AbstractMonster mo = (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i);
                        if (mo != m && !mo.isDeadOrEscaped()) {
                            AbstractCard copy = card.makeSameInstanceOf();
                            copy.tags.add(NO_DUPLICATION);
                            copy.tags.remove(SPELLCARD);
                            AbstractDungeon.player.limbo.addToBottom(copy);
                            copy.current_x = card.current_x;
                            copy.current_y = card.current_y;
                            copy.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
                            copy.target_y = Settings.HEIGHT / 2.0F;
                            copy.calculateCardDamage(mo);
                            copy.purgeOnUse = true;
                            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(copy, mo, copy.energyOnUse, true, true), true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
