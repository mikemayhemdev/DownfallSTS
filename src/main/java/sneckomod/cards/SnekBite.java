package sneckomod.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import downfall.downfallMod;
import sneckomod.SneckoMod;
import sneckomod.actions.MuddleAction;

public class SnekBite extends AbstractSneckoCard {

    public final static String ID = makeID("SnekBite");

    // Card constants
    private static final int DAMAGE = 8;
    private static final int MAGIC = 1;
    private static final int UPGRADE_DAMAGE = 1;
    private static final int UPGRADE_MAGIC = 1;


    public SnekBite() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "SnekBite.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.NONE);

        String stuff;
        if(Settings.language == Settings.GameLanguage.DEU){
            stuff = "irsst";
        } else {
            stuff = BaseMod.getKeywordProper("sneckomod:muddle");
        }
        // muddle is no longer random here
        addToBot(new SelectCardsInHandAction(magicNumber, stuff,
                (AbstractCard c) -> true,
                (cards) -> {
                    for (AbstractCard card : cards) {
                        addToBot(new MuddleAction(card));
                    }
                }
        ));
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_DAMAGE);
            upgradeMagicNumber(UPGRADE_MAGIC);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }

}
