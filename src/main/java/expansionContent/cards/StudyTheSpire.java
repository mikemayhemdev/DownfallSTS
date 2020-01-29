package expansionContent.cards;



import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.IntenseZoomEffect;
import expansionContent.powers.*;
import expansionContent.expansionContentMod;

import java.util.ArrayList;


public class StudyTheSpire extends AbstractExpansionCard {
    public final static String ID = makeID("StudyTheSpire");

    private static final int BLOCK = 8;
    private static final int UPGRADE_BLOCK = 3;
    private static final int MAGIC = 1;
    private static final int UPGRADE_MAGIC = 1;

    public StudyTheSpire() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);

        tags.add(expansionContentMod.STUDY_CHAMP);
        tags.add(expansionContentMod.STUDY);

        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        this.isMultiDamage = true;
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        atb(new VFXAction(p, new BorderFlashEffect(Color.GREEN, true), 0.05F, true));
        atb(new VFXAction(p, new IntenseZoomEffect(p.hb.cX, p.hb.cY, false), 0.05F));


        ArrayList<AbstractPower> powers = new ArrayList();


        powers.add(new StudyAutomatonPower(p, p, this.magicNumber));
        powers.add(new StudyAwakenedPower(p, p, this.magicNumber));
        powers.add(new StudyShapesPower(p, p, this.magicNumber));
        powers.add(new StudyChampPower(p, p, this.magicNumber));
        powers.add(new StudyCollectorPower(p, p, this.magicNumber));
        powers.add(new StudyGuardianPower(p, p, this.magicNumber));
        powers.add(new StudyHexaghostPower(p, p, this.magicNumber));
        powers.add(new StudyTimeEaterPower(p, p, this.magicNumber));



        AbstractPower o = powers.get(AbstractDungeon.cardRng.random(powers.size() - 1));


        atb(new ApplyPowerAction(p, p, o, this.magicNumber));


    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_BLOCK);
        }
    }

}