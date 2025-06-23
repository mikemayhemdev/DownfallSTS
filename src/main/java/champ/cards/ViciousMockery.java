package champ.cards;

import champ.ChampMod;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.city.Champ;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import static champ.ChampMod.loadJokeCardImage;

import java.util.ArrayList;

public class ViciousMockery extends AbstractChampCard {
    public final static String ID = makeID("ViciousMockery");

    public ViciousMockery() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 4;
        baseDownfallMagic = downfallMagic = 1;
        postInit();
        loadJokeCardImage(this, "ViciousMockery.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        ChampMod.vigor(magicNumber);
        applyToEnemy(m, autoWeak(m, downfallMagic));
        techique();
        atb(new SFXAction("VO_CHAMP_2A"));
        atb(new TalkAction(true, getTaunt(), 2.0F, 2.0F));
    }

    private String getTaunt() {
        ArrayList<String> derp = new ArrayList<>();
        derp.add(Champ.DIALOG[0]);
        derp.add(Champ.DIALOG[1]);
        derp.add(Champ.DIALOG[2]);
        derp.add(Champ.DIALOG[3]);
        return derp.get(MathUtils.random(derp.size() - 1));
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.STANCE.NAMES[0].toLowerCase());
    }

    public void upp() {
        //upgradeMagicNumber(1);
        upgradeDownfall(1);
    }
}