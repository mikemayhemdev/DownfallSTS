package automaton.cards;

import automaton.AutomatonMod;
import automaton.cards.encodedcards.EncodedCarnage;
import automaton.cards.encodedcards.EncodedDodgeAndRoll;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.StarBounceEffect;
import com.megacrit.cardcrawl.vfx.combat.ViolentAttackEffect;
import downfall.cardmods.EtherealMod;

public class DigitalCarnage extends AbstractBronzeCard {

    public final static String ID = makeID("DigitalCarnage");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 15;
    private static final int UPG_DAMAGE = 5;

    public DigitalCarnage() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        // isEthereal = true;
        baseDamage = DAMAGE;
        cardsToPreview = new EncodedCarnage();

        tags.add(AutomatonMod.ENCODES);
        AutomatonMod.loadJokeCardImage(this, AutomatonMod.makeBetaCardPath("DigitalCarnage.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            if (Settings.FAST_MODE) {
                this.addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED.cpy())));

                for (int i = 0; i < 5; ++i) {
                    this.addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
                }
            } else {
                this.addToBot(new VFXAction(new ViolentAttackEffect(m.hb.cX, m.hb.cY, Color.RED.cpy()), 0.4F));

                for (int i = 0; i < 5; ++i) {
                    this.addToBot(new VFXAction(new StarBounceEffect(m.hb.cX, m.hb.cY)));
                }
            }


        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        addCardToFunction(cardsToPreview.makeStatEquivalentCopy());
    }



    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        cardsToPreview.upgrade();
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}